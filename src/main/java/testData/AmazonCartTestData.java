package testData;

public enum AmazonCartTestData {

    CATEGORY_TYPE("Electronics"),
    TEXT_TO_ENTER("samsung galaxy s22"),
    TEXT_TO_SELECT("samsung galaxy s22 ultra");

    private String data;

    public String getData(){
        return this.getData();
    }

    @Override
    public String toString() {
        return this.data;
    }
    private AmazonCartTestData(String data) {
        this.data = data;
    }

}
