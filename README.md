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


# 🚕 Uber-like Ride Booking System

## 📌 Overview

This project is a **real-time Uber-style ride booking system** built to demonstrate how modern backend systems handle **location-based matching**, **real-time communication**, and **microservice interaction**.

Passengers can create ride requests, the system efficiently finds nearby drivers using **Redis GEO (geohashing)**, and drivers are notified instantly using **WebSockets**.  
Inter-service communication is handled using **gRPC with Protocol Buffers** for high performance and strong typing.

---

## 🧠 High-Level Architecture

