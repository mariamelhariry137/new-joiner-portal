ALTER TABLE checklist_items ADD COLUMN IF NOT EXISTS description VARCHAR(500);

UPDATE checklist_items SET description = 'Order your laptop, get accounts provisioned, and confirm you can log in to everything you need.' WHERE id = 1 AND (description IS NULL OR description = '');
UPDATE checklist_items SET description = 'Reach out to your assigned onboarding buddy to introduce yourself and schedule a first chat.' WHERE id = 2 AND (description IS NULL OR description = '');
UPDATE checklist_items SET description = 'Fill out and submit all required HR forms and paperwork.' WHERE id = 3 AND (description IS NULL OR description = '');
UPDATE checklist_items SET description = 'Read through the company handbook to understand policies, culture, and expectations.' WHERE id = 4 AND (description IS NULL OR description = '');