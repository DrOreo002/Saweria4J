# Saweria4J
A simple Java API for Saweria.co, currently only handles donations event. Feel free to open a **Pull Request** if you want to add more!

## Usage
```java
new SaweriaClient(MY_STREAM_KEY, new SaweriaEventHandler() {
    @Override
    public void onDonationReceived(SaweriaDonation[] donations) {
        for (SaweriaDonation donation : donations) {
            System.out.println(donation);
        }
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClosed() {

    }
}).start();
```
