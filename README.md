# ETFBL_IP - Electric Vehicle Rental System

## Project Description

ETFBL_IP is a complete electric vehicle rental management system developed for the Faculty of Electrical Engineering. The system enables rental operations for electric cars, bicycles, and scooters through three specialized applications with role-based access control.

## Key Features

### Core Functionality
- **Multi-type vehicle management** (cars, bikes, scooters)
- **Rental transaction processing** with PDF invoice generation
- **User management** (customers and employees)
- **Maintenance tracking system**
- **Promotion and marketing content management**
- **RSS feed** for latest promotions

### Technical Highlights
- **Angular 15+** with Material Design components for admin portal
- **Spring Boot 3** REST API with JPA/Hibernate
- **MySQL** relational database
- **JSP** for customer-facing mobile interfaces
- **Location tracking** using coordinate system
- **CSV import/export** for vehicle data

## System Components

### 1. Backend Services (Spring Boot)
- **Vehicle CRUD operations** with type-specific attributes
- **Rental transaction processing** with document verification
- **User authentication** and role-based authorization
- **Maintenance record management**
- **Promotion content delivery** with RSS feed
- **PDF invoice generation**

### 2. Employee Portal (Angular)
- **Admin Features**:
  - Full vehicle lifecycle management
  - Manufacturer CRUD operations
  - User account administration
  - CSV data import/export
  
- **Operator Features**:
  - Rental monitoring dashboard
  - Map view of vehicle locations
  - Customer account management
  - Maintenance reporting

- **Manager Features**:
  - Financial statistics and analytics
  - Pricing management
  - Business performance dashboards

### 3. Customer App (JSP)
- Mobile-optimized rental interface
- Profile management
- Rental history tracking
- Payment processing
- Location services integration

### 4. Promotions App (JSP)
- Marketing content creation
- Campaign management
- Promotion scheduling
