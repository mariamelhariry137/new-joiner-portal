ALTER TABLE checklist_items ALTER COLUMN description SET DEFAULT '';
UPDATE checklist_items SET description = '' WHERE description IS NULL;
