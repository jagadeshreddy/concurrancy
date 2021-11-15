package concurrancy.tokenbucketfilter.factory;

public class TokenBucketFilterFactory  {

    private class MultiThreadedTokenBucketFilter extends TokenBucketFilter{
        private long possibleTokens = 0;
        private final int MAX_TOKENS;
        private final int ONE_SECOND = 1000;

        MultiThreadedTokenBucketFilter(int max_tokens) {
            MAX_TOKENS = max_tokens;

        }
        private void demonThread() {
            while (true) {
                synchronized (this) {
                    if (this.MAX_TOKENS > possibleTokens) {
                        possibleTokens++;
                    }
                    this.notify();
                }
                try {
                    Thread.sleep(ONE_SECOND);
                } catch (InterruptedException e) {

                }
            }
        }

        void getToken() throws InterruptedException {
            synchronized (this) {
                while (possibleTokens == 0) {
                    this.wait();
                }
                possibleTokens--;
            }
            System.out.println("Granting " + Thread.currentThread().getName());
        }


        @Override
        public TokenBucketFilter makeTokenBucketFilter() {
            return null;
        }
    }




}
