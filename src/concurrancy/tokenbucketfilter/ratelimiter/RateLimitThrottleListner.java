package concurrancy.tokenbucketfilter.ratelimiter;

class RateLimitThrottleListener implements RateLimitListener
{
    public RateLimitThrottleListener()
    {
    }
    public void rateLimitThresholdBreached()
    {
        System.out.println("Received threshold breach callback notification");
    }
}
