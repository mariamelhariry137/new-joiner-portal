-- Seed demo teams data
INSERT INTO teams (name, description)
SELECT * FROM (
                  VALUES
                      ('IT Support', 'Handles all technical infrastructure, hardware, and software support for employees'),
                      ('Human Resources', 'Manages employee relations, benefits, recruitment, and company culture'),
                      ('Finance', 'Oversees budgeting, accounting, payroll, and financial planning'),
                      ('Marketing', 'Responsible for brand management, campaigns, content creation, and market research'),
                      ('Operations', 'Manages day-to-day business operations, facilities, and process optimization'),
                      ('Engineering', 'Develops and maintains software products and technical solutions'),
                      ('Sales', 'Drives revenue through client acquisition, account management, and business development'),
                      ('Legal', 'Handles contracts, compliance, intellectual property, and regulatory matters'),
                      ('Product', 'Manages product strategy, roadmap, and feature development'),
                      ('Customer Success', 'Ensures client satisfaction, retention, and adoption of products')
              ) AS data(name, description)
WHERE NOT EXISTS (
    SELECT 1 FROM teams WHERE teams.name = data.name
);