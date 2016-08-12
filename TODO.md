Planned:

* OSGi metadata (so we have a dual mode jar)
* synchronized wrapper
* use different test framework that has assertLessThan or assertInRange (so we get to 100% branch coverage -- using a comparison operator in a call to assertTrue means only one branch will be covered)
* wrapper class that stores RateLimiter instance for client identifiers (e.g. per IP) and avoids opening us up for DDOS attacks
