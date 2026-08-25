# Auth Service

## Overview

The Auth Service handles user registration, login, authentication,
JWT access tokens, and role-based authorization.

## Port

The service runs on:

`8081`

## Endpoints

### Register

`POST /api/auth/register`

### Login

`POST /api/auth/login`

### Current User

`GET /me`

Requires a Bearer JWT token.

## Roles

- `NEW_JOINER`
- `ADMIN`