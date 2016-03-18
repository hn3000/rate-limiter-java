Planned:

* OSGi metadata (so we have a dual mode jar)
* synchronized wrapper
* use different test framework that has assertLessThan or assertInRange (so we get to 100% test coverage)
* wrapper class that stores RateLimiter instance for client identifiers (e.g. per IP) and avoids opening us up for DDOS attacks
