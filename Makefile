# Makefile for Spring Boot Microservices v0.3

DEBUG_PORT ?= 5005

.PHONY: run clean test

default: run

help:
	@echo "Available targets:"
	@echo "  run    Start DB container and run the project (default)"
	@echo "  test   Run all tests"
	@echo "  clean  Clean the project"
	@echo "  debug  Start the project through Maven with enabled debug on port $(DEBUG_PORT)"

run:
	docker compose up -d
	./mvnw spring-boot:run

clean:
	docker compose down -v
	./mvnw clean

test:
	./mvnw clean test -ntp

debug:
	./mvnw spring-boot:run -Pdebug
