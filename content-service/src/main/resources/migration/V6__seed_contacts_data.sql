-- Seed demo contacts data
INSERT INTO contacts (name, email, phone, team_id)
SELECT * FROM (
                  VALUES
                      ('John Smith', 'john.smith@company.com', '+1-555-0101', (SELECT id FROM teams WHERE name = 'IT Support')),
                      ('Sarah Johnson', 'sarah.johnson@company.com', '+1-555-0102', (SELECT id FROM teams WHERE name = 'Human Resources')),
                      ('Michael Brown', 'michael.brown@company.com', '+1-555-0103', (SELECT id FROM teams WHERE name = 'Finance')),
                      ('Emily Davis', 'emily.davis@company.com', '+1-555-0104', (SELECT id FROM teams WHERE name = 'Marketing')),
                      ('David Wilson', 'david.wilson@company.com', '+1-555-0105', (SELECT id FROM teams WHERE name = 'Operations')),
                      ('Lisa Anderson', 'lisa.anderson@company.com', '+1-555-0106', (SELECT id FROM teams WHERE name = 'Engineering')),
                      ('Robert Taylor', 'robert.taylor@company.com', '+1-555-0107', (SELECT id FROM teams WHERE name = 'Sales')),
                      ('Jennifer Martinez', 'jennifer.martinez@company.com', '+1-555-0108', (SELECT id FROM teams WHERE name = 'Legal')),
                      ('William Garcia', 'william.garcia@company.com', '+1-555-0109', (SELECT id FROM teams WHERE name = 'Product')),
                      ('Patricia Robinson', 'patricia.robinson@company.com', '+1-555-0110', (SELECT id FROM teams WHERE name = 'Customer Success'))
              ) AS data(name, email, phone, team_id)
WHERE NOT EXISTS (
    SELECT 1 FROM contacts WHERE contacts.email = data.email
);