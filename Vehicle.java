public abstract class Vehicle {
    protected int voyageId;
    protected String voyageFrom;
    protected String voyageTo;
    protected int seatRows;
    protected int numberOfStandardSeats;
    protected float standardSeatPrice;
    protected boolean[] availableSeats;
    protected float revenue;

    public Vehicle(int voyageId, String voyageFrom, String voyageTo, int seatRows, float standardSeatPrice) {
        this.voyageId = voyageId;
        this.voyageFrom = voyageFrom;
        this.voyageTo = voyageTo;
        this.seatRows = seatRows;
        this.standardSeatPrice = standardSeatPrice;
        this.numberOfStandardSeats = numberOfStandardSeats(seatRows);
    }

    public int getVoyageId() {
        return voyageId;
    }

    public String getVoyageFrom() {
        return voyageFrom;
    }

    public String getVoyageTo() {
        return voyageTo;
    }

    public float getStandardSeatPrice() {
        return standardSeatPrice;
    }

    public boolean[] getAvailableSeats() {
        return availableSeats;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public boolean isSeatAvailable(int seatNumber) {
        return availableSeats[seatNumber - 1];
    }

    public void markSeatAsSold(int seatNumber) {
        availableSeats[seatNumber - 1] = false;
    }

    public void markSeatAsAvailable(int seatNumber) {
        availableSeats[seatNumber - 1] = true;
    }

    protected abstract int numberOfStandardSeats(int seatRows);

    public abstract void displayVehicle(String outputFile);
}