# Build phase
FROM rust:1.52.1-slim-buster as builder
WORKDIR /build/app

# Build the application
COPY . .
RUN cargo build --release

# Copy the build and create the image to run
FROM debian:buster-slim
WORKDIR /app
COPY --from=builder /build/app/target/release/resting-api /app/resting-api

EXPOSE 3030

CMD ["./resting-api"]
