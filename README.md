### Rate Limiter Proof of Concept (POC) Implementation in Java
This POC is based on the book "System Design Interview" by Alex Xu, which discusses the importance of rate limiting in system design. The five rate limiter algorithms mentioned above have been implemented in Java to test their effectiveness in different scenarios.

The implementation includes the following features:

* Each algorithm has its own class with a `isAllowed` method that takes a request timestamp as input and returns a boolean indicating whether the request is allowed or not.
* Unit tests have been written for each algorithm to ensure that they are working correctly.
* The implementation uses Java's built-in concurrency features to simulate multiple requests and test the algorithms under load.

The implementation can be used as a starting point for building a rate limiter system in a real-world application. The algorithms can be further optimized and customized to suit specific use cases.

### Unit Tests
The unit tests for each algorithm are designed to test the following scenarios:

* A single request is allowed within the rate limit.
* Multiple requests are allowed within the rate limit.
* A request is blocked when the rate limit is exceeded.
* The rate limit is reset after a certain period of time.

The tests use Java's built-in `JUnit` framework and are run using the `maven` build tool.

### Conclusion
The five rate limiter algorithms implemented in this POC demonstrate the different approaches to controlling the rate at which requests are processed. The implementation provides a starting point for building a rate limiter system in a real-world application and can be further optimized and customized to suit specific use cases. The unit tests ensure that each algorithm is working correctly and provide a basis for testing and validating the implementation.

### Rate Limiter Algorithms
The following are five different rate limiter algorithms used to control the rate at which requests are processed:

#### 1. Leaking Bucket Algorithm
* **Description**: The leaking bucket algorithm is a simple rate limiter that uses a bucket to store tokens. Tokens are added to the bucket at a constant rate, and each request consumes one token. If the bucket is empty, the request is blocked.
* **Characteristics**:
  + Simple to implement
  + Can handle bursty traffic
  + Not suitable for applications with varying request rates
* **Use Cases**: Network traffic shaping, API rate limiting

#### 2. Token Bucket Algorithm
* **Description**: The token bucket algorithm is similar to the leaking bucket algorithm, but it allows for bursty traffic by adding tokens to the bucket at a variable rate.
* **Characteristics**:
  + More flexible than the leaking bucket algorithm
  + Can handle varying request rates
  + More complex to implement
* **Use Cases**: Network traffic shaping, API rate limiting, resource allocation

#### 3. Fixed Window Algorithm
* **Description**: The fixed window algorithm divides time into fixed intervals (windows) and allows a certain number of requests within each window.
* **Characteristics**:
  + Simple to implement
  + Can lead to bursty traffic at the start of each window
  + Not suitable for applications with varying request rates
* **Use Cases**: Simple API rate limiting, resource allocation

#### 4. Sliding Window Algorithm
* **Description**: The sliding window algorithm is similar to the fixed window algorithm, but it uses a sliding window that moves forward in time. This allows for more flexible rate limiting and reduces the likelihood of bursty traffic.
* **Characteristics**:
  + More flexible than the fixed window algorithm
  + Can handle varying request rates
  + More complex to implement
* **Use Cases**: API rate limiting, resource allocation, network traffic shaping

#### 5. Sliding Window Counter Algorithm
* **Description**: The sliding window counter algorithm uses a counter to track the number of requests within a sliding window. If the counter exceeds a certain threshold, the algorithm blocks new requests.
* **Characteristics**:
  + Similar to the sliding window algorithm, but uses a counter instead of a queue
  + Can be more efficient than the sliding window algorithm for large windows
  + More complex to implement
* **Use Cases**: API rate limiting, resource allocation, network traffic shaping

### Comparison of Rate Limiter Algorithms

| Algorithm | Complexity | Flexibility | Bursty Traffic Handling |
| --- | --- | --- | --- |
| Leaking Bucket | Low | Low | Good |
| Token Bucket | Medium | Medium | Good |
| Fixed Window | Low | Low | Poor |
| Sliding Window | Medium | High | Good |
| Sliding Window Counter | Medium | High | Good |