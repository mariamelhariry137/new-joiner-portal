# Spring Boot Project Setup - Maven Installation Guide

## Prerequisites
- Java 17 or higher (you currently have Java 8 - consider upgrading)
- Maven 3.9.0 or higher

## Installation Steps

### 1. Download Maven
Visit: https://maven.apache.org/download.cgi
- Download the Binary zip archive (apache-maven-3.9.x-bin.zip)

### 2. Extract Maven
- Extract to a directory (e.g., C:\Maven or C:\Program Files\Apache\Maven)

### 3. Set Environment Variables
- Add MAVEN_HOME: `C:\path\to\maven`
- Add to PATH: `%MAVEN_HOME%\bin`

### 4. Verify Installation
Open PowerShell and run:
```powershell
mvn --version
```

### 5. Build Project
Once Maven is installed, run from the project root:
```powershell
mvn clean install
```

## Project Structure Created

### Services:
1. **API Gateway** (Port 8080)
   - Spring Cloud Gateway
   - Routes requests to other services

2. **Content Service** (Port 8081)
   - REST API for content management
   - Connected to PostgreSQL

3. **Onboarding Service** (Port 8082)
   - REST API for onboarding workflows
   - Connected to PostgreSQL

## Files Created

✅ pom.xml (root parent)
✅ gateway/pom.xml with Spring Cloud Gateway dependencies
✅ content-service/pom.xml with JPA + PostgreSQL
✅ onboarding-service/pom.xml with JPA + PostgreSQL
✅ Application classes for each service
✅ application.yml configuration files
✅ .gitignore for Maven projects

## Next Steps

1. Install Maven (see steps above)
2. Update Java to version 17+ (recommended)
3. Run: `mvn clean install`
4. Start PostgreSQL (see infra/docker-compose.yml)
5. Run each service:
   - Gateway: `mvn -pl gateway spring-boot:run`
   - Content Service: `mvn -pl content-service spring-boot:run`
   - Onboarding Service: `mvn -pl onboarding-service spring-boot:run`

## Default Configuration
- Gateway: http://localhost:8080
- Content Service: http://localhost:8081/api
- Onboarding Service: http://localhost:8082/api
- PostgreSQL: localhost:5432 (user: postgres, password: postgres)

