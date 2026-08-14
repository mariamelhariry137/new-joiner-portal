-- Seed demo learning resources data
INSERT INTO learning_resources (title, description, url)
SELECT * FROM (
                  VALUES
                      ('Welcome to Company', 'Introduction to company culture, values, and mission for new employees', 'https://company.com/onboarding/welcome'),
                      ('IT Setup Guide', 'Step-by-step instructions for setting up work equipment and accessing internal systems', 'https://company.com/onboarding/it-setup'),
                      ('HR Benefits Overview', 'Comprehensive breakdown of health insurance, retirement plans, and other benefits', 'https://company.com/benefits/overview'),
                      ('Project Management 101', 'Overview of project management tools and methodologies used at the company', 'https://company.com/learning/project-management'),
                      ('Company Tools Guide', 'Introduction to Slack, Jira, Confluence, and other essential tools', 'https://company.com/learning/tools-guide'),
                      ('Team Communication', 'Best practices for effective communication across teams and with management', 'https://company.com/learning/communication'),
                      ('Security Training', 'Mandatory training on recognizing phishing attempts and maintaining data security', 'https://company.com/training/security'),
                      ('Diversity and Inclusion', 'Company commitment to diversity, inclusion, and creating a welcoming environment', 'https://company.com/learning/diversity'),
                      ('Product Knowledge', 'Understanding our products, features, and value proposition to customers', 'https://company.com/learning/product'),
                      ('Career Development', 'Pathways for professional growth, mentorship opportunities, and skill building', 'https://company.com/learning/career-development')
              ) AS data(title, description, url)
WHERE NOT EXISTS (
    SELECT 1 FROM learning_resources WHERE learning_resources.title = data.title
);