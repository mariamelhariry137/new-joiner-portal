package com.newjoinerportal.onboarding.service;

import com.newjoinerportal.onboarding.dto.ChecklistProgressResponse;
import com.newjoinerportal.onboarding.dto.MarkCompletionResponse;
import com.newjoinerportal.onboarding.ChecklistCompletion;
import com.newjoinerportal.onboarding.ChecklistItem;
import com.newjoinerportal.onboarding.repository.ChecklistCompletionRepository;
import com.newjoinerportal.onboarding.repository.ChecklistItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChecklistCompletionIsolationTest {

    @Mock
    private ChecklistItemRepository checklistItemRepository;

    @Mock
    private ChecklistCompletionRepository checklistCompletionRepository;

    @InjectMocks
    private ChecklistCompletionService checklistCompletionService;

    private final Long userA = 1L;
    private final Long userB = 2L;
    private ChecklistItem item;

    @BeforeEach
    void setUp() {
        item = new ChecklistItem("Set up laptop", 1);
    }

    @Test
    void getUserProgress_userA_neverSeesUserBsCompletions() {
        when(checklistItemRepository.findAllByOrderByOrderIndexAsc()).thenReturn(List.of(item));

        // Only stub what userA's lookup returns — userB's data is deliberately never configured,
        // so if the service ever queried userB's data for userA's request, this test would fail
        // with an "unnecessary stubbing" or unexpected null, not silently pass.
        when(checklistCompletionRepository.findAllByUserId(userA))
                .thenReturn(List.of(new ChecklistCompletion(userA, item.getId(), true)));

        List<ChecklistProgressResponse> result = checklistCompletionService.getUserProgress(userA);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).isCompleted()).isTrue();

        // Prove the service asked for userA's data specifically, and NEVER asked for userB's
        verify(checklistCompletionRepository).findAllByUserId(userA);
        verify(checklistCompletionRepository, never()).findAllByUserId(userB);
    }

    @Test
    void markCompletion_userA_onlyLooksUpAndSavesUnderUserAsId() {
        Long itemId = item.getId();

        when(checklistItemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(checklistItemRepository.count()).thenReturn(1L);
        when(checklistCompletionRepository.findByUserIdAndChecklistItemId(userA, itemId))
                .thenReturn(Optional.empty());
        when(checklistCompletionRepository.save(any(ChecklistCompletion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(checklistCompletionRepository.findAllByUserId(userA))
                .thenReturn(List.of(new ChecklistCompletion(userA, itemId, true)));

        checklistCompletionService.markCompletion(userA, itemId, true);

        // Confirm the lookup was scoped to userA's id specifically
        verify(checklistCompletionRepository).findByUserIdAndChecklistItemId(userA, itemId);
        verify(checklistCompletionRepository, never()).findByUserIdAndChecklistItemId(userB, itemId);

        // Confirm whatever got saved actually belongs to userA, not userB
        verify(checklistCompletionRepository).save(argThat(completion ->
                completion.getUserId().equals(userA)
        ));
    }

    @Test
    void twoUsersMarkingTheSameItem_createSeparateCompletionRecords() {
        Long itemId = item.getId();

        when(checklistItemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(checklistItemRepository.count()).thenReturn(1L);
        when(checklistCompletionRepository.save(any(ChecklistCompletion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // userA has no existing record; userB has no existing record either — separate, independent lookups
        when(checklistCompletionRepository.findByUserIdAndChecklistItemId(userA, itemId))
                .thenReturn(Optional.empty());
        when(checklistCompletionRepository.findByUserIdAndChecklistItemId(userB, itemId))
                .thenReturn(Optional.empty());
        when(checklistCompletionRepository.findAllByUserId(userA))
                .thenReturn(List.of(new ChecklistCompletion(userA, itemId, true)));
        when(checklistCompletionRepository.findAllByUserId(userB))
                .thenReturn(List.of(new ChecklistCompletion(userB, itemId, false)));

        MarkCompletionResponse responseA = checklistCompletionService.markCompletion(userA, itemId, true);
        MarkCompletionResponse responseB = checklistCompletionService.markCompletion(userB, itemId, false);

        // Same item, but each user's result reflects only their own action
        assertThat(responseA.completed()).isTrue();
        assertThat(responseB.completed()).isFalse();

        // Two separate save() calls happened, one scoped to each user
        verify(checklistCompletionRepository).save(argThat(c -> c.getUserId().equals(userA)));
        verify(checklistCompletionRepository).save(argThat(c -> c.getUserId().equals(userB)));
    }
}