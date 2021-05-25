FROM golang:1.16.4-alpine3.13 as builder
# Set workspace directory and copy over the source files
WORKDIR /app
COPY . .

# Set the go environment variables for building the app
ENV PATH=$PATH:/app
RUN go env -w GOBIN=/app

RUN go get -d -v ./...
RUN go install -v ./...

# Use alpine as the container
FROM alpine:3.13
WORKDIR /app
ENV GIN_MODE=release
COPY .env .env
COPY --from=builder /app/RestAPI /app/RestAPI

EXPOSE 8080

CMD ["./RestAPI"]
