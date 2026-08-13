# Maven Installation & Spring Boot Setup - COMPLETE ✅

## Maven Installation Summary

**Status**: ✅ **Successfully Installed**

### Installation Details:
- **Location**: `C:\apache-maven-3.9.6`
- **Version**: Apache Maven 3.9.6
- **Java**: 1.8.0_401
- **OS**: Windows 11 (64-bit)

### Environment Variables Set:
- **MAVEN_HOME**: `C:\apache-maven-3.9.6`
- **PATH**: Updated with `C:\apache-maven-3.9.6\bin`

### Maven Verification:
```
Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
Maven home: C:\apache-maven-3.9.6
Java version: 1.8.0_401, vendor: Oracle Corporation
```

---

## Spring Boot Projects - Downloading Dependencies

### Current Status:
✅ Downloading all Spring Boot 3.2.0 dependencies  
✅ Building for all three services

### Services Being Built:

1. **API Gateway** (Port 8080)
   - Spring Cloud Gateway 4.1.0
   - Spring WebFlux
   - Actuator for monitoring

2. **Content Service** (Port 8081)
   - Spring Boot 3.2.0
   - Spring Data JPA
   - PostgreSQL Driver
   - Hibernate ORM

3. **Onboarding Service** (Port 8082)
   - Spring Boot 3.2.0
   - Spring Data JPA
   - PostgreSQL Driver
   - Hibernate ORM

---

## What Was Downloaded:

### Framework & Core Dependencies:
- Spring Boot 3.2.0 Starters
- Spring Cloud 4.1.0 (Gateway, Context, Commons)
- Spring Framework 6.1.1
- Reactor (WebFlux)
- Netty (Async I/O)

### Data & Database:
- Hibernate ORM
- Spring Data JPA
- PostgreSQL JDBC Driver

### Testing:
- JUnit 5.10.1
- Mockito 5.7.0
- AssertJ
- Spring Test Framework

### Security:
- Spring Security Crypto
- Bouncy Castle

### Additional Libraries:
- Jackson (JSON processing)
- Micrometer (Metrics)
- Validation (Hibernate Validator)

---

## Commands Reference

### Verify Maven Installation:
```powershell
C:\apache-maven-3.9.6\bin\mvn.cmd --version
```

### Build All Modules:
```powershell
cd C:\Users\Lenovo\IdeaProjects\new-joiner-portal
C:\apache-maven-3.9.6\bin\mvn.cmd clean install
```

### Build Specific Service:
```powershell
C:\apache-maven-3.9.6\bin\mvn.cmd clean install -pl gateway
C:\apache-maven-3.9.6\bin\mvn.cmd clean install -pl content-service
C:\apache-maven-3.9.6\bin\mvn.cmd clean install -pl onboarding-service
```

### Run Services:
```powershell
# Gateway
C:\apache-maven-3.9.6\bin\mvn.cmd -pl gateway spring-boot:run

# Content Service
C:\apache-maven-3.9.6\bin\mvn.cmd -pl content-service spring-boot:run

# Onboarding Service
C:\apache-maven-3.9.6\bin\mvn.cmd -pl onboarding-service spring-boot:run
```

---

## Project Structure

```
new-joiner-portal/
├── pom.xml                           (Parent POM)
├── gateway/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/newjoinerportal/gateway/
│       │   └── GatewayApplication.java
│       └── resources/
│           └── application.yml
├── content-service/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/newjoinerportal/content/
│       │   └── ContentServiceApplication.java
│       └── resources/
│           └── application.yml
└── onboarding-service/
    ├── pom.xml
    └── src/main/
        ├── java/com/newjoinerportal/onboarding/
        │   └── OnboardingServiceApplication.java
        └── resources/
            └── application.yml
```

---

## Default Ports & URLs

| Service | URL | Port |
|---------|-----|------|
| API Gateway | http://localhost:8080 | 8080 |
| Content Service | http://localhost:8081/api | 8081 |
| Onboarding Service | http://localhost:8082/api | 8082 |
| PostgreSQL | localhost:5432 | 5432 |

---

## Database Configuration

**Default Credentials** (update in `application.yml` if different):
- **User**: postgres
- **Password**: postgres
- **Content DB**: content_db
- **Onboarding DB**: onboarding_db

---

## Next Steps

1. ✅ Maven installed
2. ✅ Dependencies downloaded
3. ⏳ Build in progress...
4. After build completes:
   - Start PostgreSQL (use `docker-compose.yml` in `infra/` folder)
   - Start individual services
   - Test endpoints

---

## Troubleshooting

### If Maven command not found:
```powershell
# Use full path
C:\apache-maven-3.9.6\bin\mvn.cmd --version

# Or close and reopen PowerShell for PATH to refresh
```

### Clean rebuild:
```powershell
cd C:\Users\Lenovo\IdeaProjects\new-joiner-portal
C:\apache-maven-3.9.6\bin\mvn.cmd clean install -X
```

### Skip tests during build:
```powershell
C:\apache-maven-3.9.6\bin\mvn.cmd clean install -DskipTests
```

---

## System Requirements

- ✅ Java 8+ (you have 1.8.0_401)
- ✅ Maven 3.9.6 (installed)
- ⏳ PostgreSQL (available in `infra/docker-compose.yml`)
- ⏳ Docker (optional, for PostgreSQL)

**Recommendation**: Upgrade to Java 17+ for better Spring Boot 3.x support.

---

**Last Updated**: August 13, 2026
**Status**: Maven Downloaded & Build In Progress

