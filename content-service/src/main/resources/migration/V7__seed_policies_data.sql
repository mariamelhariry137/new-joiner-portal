-- Seed demo policies data
INSERT INTO policies (title, description)
SELECT * FROM (
                  VALUES
                      ('Employee Handbook', 'Comprehensive guide covering company policies, code of conduct, work hours, and employee rights'),
                      ('IT Security Policy', 'Guidelines for maintaining information security, password management, and data protection'),
                      ('Remote Work Policy', 'Procedures and expectations for employees working remotely, including equipment and communication'),
                      ('Leave Policy', 'Details on vacation days, sick leave, personal time off, and holiday schedule'),
                      ('Code of Conduct', 'Standards of professional behavior, ethics, and workplace expectations'),
                      ('Expense Reimbursement', 'Process for submitting and approving business expense claims'),
                      ('Data Privacy Policy', 'Rules for handling personal and sensitive company data in compliance with regulations'),
                      ('Performance Review Process', 'Annual and quarterly evaluation procedures, feedback collection, and career development'),
                      ('Anti-Harassment Policy', 'Zero-tolerance policy against workplace harassment and discrimination'),
                      ('Dress Code Policy', 'Guidelines for appropriate workplace attire and professional appearance')
              ) AS data(title, description)
WHERE NOT EXISTS (
    SELECT 1 FROM policies WHERE policies.title = data.title
);