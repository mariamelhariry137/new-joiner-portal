package com.newjoinerportal.onboarding.service;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChecklistCompletionServiceTest {

    @Mock
    private ChecklistItemRepository checklistItemRepository;

    @Mock
    private ChecklistCompletionRepository checklistCompletionRepository;

    @InjectMocks
    private ChecklistCompletionService checklistCompletionService;

    private ChecklistItem item1;
    private ChecklistItem item2;

    @BeforeEach
    void setUp() {
        item1 = new ChecklistItem("Set up laptop", 1);
        item2 = new ChecklistItem("Meet your buddy", 2);
    }

    @Test
    void markCompletion_oneOfFourItemsDone_returnsTwentyFivePercent() {
        Long userId = 1L;
        Long itemId = 1L;

        when(checklistItemRepository.findById(itemId)).thenReturn(Optional.of(item1));
        when(checklistItemRepository.count()).thenReturn(4L);
        when(checklistCompletionRepository.findByUserIdAndChecklistItemId(userId, itemId))
                .thenReturn(Optional.empty());
        when(checklistCompletionRepository.save(any(ChecklistCompletion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(checklistCompletionRepository.findAllByUserId(userId))
                .thenReturn(List.of(new ChecklistCompletion(userId, itemId, true)));

        MarkCompletionResponse response = checklistCompletionService.markCompletion(userId, itemId, true);

        assertThat(response.completed()).isTrue();
        assertThat(response.completionPercentage()).isEqualTo(25.0);
    }

    @Test
    void markCompletion_twoOfFourItemsDone_returnsFiftyPercent() {
        Long userId = 1L;
        Long itemId = 2L;

        when(checklistItemRepository.findById(itemId)).thenReturn(Optional.of(item2));
        when(checklistItemRepository.count()).thenReturn(4L);
        when(checklistCompletionRepository.findByUserIdAndChecklistItemId(userId, itemId))
                .thenReturn(Optional.empty());
        when(checklistCompletionRepository.save(any(ChecklistCompletion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(checklistCompletionRepository.findAllByUserId(userId))
                .thenReturn(List.of(
                        new ChecklistCompletion(userId, 1L, true),
                        new ChecklistCompletion(userId, 2L, true)
                ));

        MarkCompletionResponse response = checklistCompletionService.markCompletion(userId, itemId, true);

        assertThat(response.completionPercentage()).isEqualTo(50.0);
    }

    @Test
    void markCompletion_markingIncomplete_excludesFromPercentage() {
        Long userId = 1L;
        Long itemId = 1L;
        ChecklistCompletion existing = new ChecklistCompletion(userId, itemId, true);

        when(checklistItemRepository.findById(itemId)).thenReturn(Optional.of(item1));
        when(checklistItemRepository.count()).thenReturn(4L);
        when(checklistCompletionRepository.findByUserIdAndChecklistItemId(userId, itemId))
                .thenReturn(Optional.of(existing));
        when(checklistCompletionRepository.save(any(ChecklistCompletion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(checklistCompletionRepository.findAllByUserId(userId))
                .thenReturn(List.of(new ChecklistCompletion(userId, itemId, false)));

        MarkCompletionResponse response = checklistCompletionService.markCompletion(userId, itemId, false);

        assertThat(response.completed()).isFalse();
        assertThat(response.completedAt()).isNull();
        assertThat(response.completionPercentage()).isEqualTo(0.0);
    }

    @Test
    void markCompletion_zeroTotalItems_returnsZeroPercentWithoutError() {
        Long userId = 1L;
        Long itemId = 1L;

        when(checklistItemRepository.findById(itemId)).thenReturn(Optional.of(item1));
        when(checklistItemRepository.count()).thenReturn(0L);
        when(checklistCompletionRepository.findByUserIdAndChecklistItemId(userId, itemId))
                .thenReturn(Optional.empty());
        when(checklistCompletionRepository.save(any(ChecklistCompletion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MarkCompletionResponse response = checklistCompletionService.markCompletion(userId, itemId, true);

        assertThat(response.completionPercentage()).isEqualTo(0.0);
        verify(checklistCompletionRepository, never()).findAllByUserId(any());
    }

    @Test
    void markCompletion_unknownItem_throwsNotFoundException() {
        Long userId = 1L;
        Long unknownItemId = 999L;

        when(checklistItemRepository.findById(unknownItemId)).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                ChecklistItemNotFoundException.class,
                () -> checklistCompletionService.markCompletion(userId, unknownItemId, true)
        );
    }
}