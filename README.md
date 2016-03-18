A Simple Rate Limiter Implementation

For security applications, e.g. when exposing an API, a rate limiter can help mitigate flooding.

In order to use the least amount of instructions for this rate-limiting activity,
a simple rate limiter implementation using the [token bucket] algorithm is provided.

Having 100% branch coverage by unit tests should help ensure that the code
can be used for actually making an API safer to expose. (It can not make your
API safer, but if we avoid not limiting the rate of actual calls performed,
we might at least not make it worse.)

While the guava rate limiter has some cool ideas about behavior after lull periods,
the default rate limiting algorithm seems to be SmoothBursty -- the behavior of which,
as far as I can tell, is more easily implementend using the [token bucket] algorithm.

Also, I wanted to make the implementation simpler by separating concerns: the
guava rate limiter mixes synchronization, waiting and the actual rate limiting
algorithm in two classes (and some helpers), this project has

* a rate limiter interface
* an implementation using the token bucket algorithm (others are possible)

these are not done yet:
* an implementation of synchronization (making it thread safe in a way that seems to have obviously no errors, as opposed to no obvious errors)
* a wrapper for waiting until tokens are available (which is interruptible, because I don't see why it should not)

---
[token bucket]: https://en.wikipedia.org/wiki/Token_bucket
