# 🚕 Real-Time Ride Booking Platform

## 📌 Overview

A microservices-based ride booking platform that demonstrates location-aware ride matching, real-time communication, and scalable backend service interaction.

Passengers can create ride requests, while nearby drivers are discovered efficiently using Redis GEO spatial indexing. Driver notifications are delivered in real time through WebSockets, and inter-service communication is handled using gRPC and Protocol Buffers for low-latency, strongly typed messaging.

---

## 🏗️ Project Structure

```text
booking-service
├── controllers
├── services
├── repositories
└── grpc-client

socket-service
├── websocket-config
├── grpc-server
└── socket-publisher

frontend
├── passenger-ui
└── driver-ui
```

---

## ✨ Key Features

* Real-time ride request creation and management
* Location-based driver discovery using Redis GEO
* Instant driver notifications via WebSockets
* High-performance service-to-service communication using gRPC
* Protocol Buffers for efficient data serialization
* Separate passenger and driver interfaces

---

## 🛠️ Tech Stack

**Backend:** Spring Boot, gRPC, Protocol Buffers

**Database & Caching:** Redis GEO

**Communication:** WebSockets

**Build Tools:** Maven

**Frontend:** Passenger UI, Driver UI
