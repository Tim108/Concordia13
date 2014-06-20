package rest;

public class Art {
	int id;
	String name;
	String source;
	String artist;
	double height;
	double width;
	String style;
	String technique;
	String orientation;
	double price;
	double rating;
	boolean rented;
	
	public Art(int id, String name, String source, String artist,
			double height, double width, String style, String technique,
			String orientation, double price, double rating, boolean rented) {
		this.id = id;
		this.name = name;
		this.source = source;
		this.artist = artist;
		this.height = height;
		this.width = width;
		this.style = style;
		this.technique = technique;
		this.orientation = orientation;
		this.price = price;
		this.rating = rating;
		this.rented = rented;
	}
	
	protected int getId() {
		return id;
	}
	protected void setId(int id) {
		this.id = id;
	}
	protected String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	protected String getSource() {
		return source;
	}
	protected void setSource(String source) {
		this.source = source;
	}
	protected String getArtist() {
		return artist;
	}
	protected void setArtist(String artist) {
		this.artist = artist;
	}
	protected double getHeight() {
		return height;
	}
	protected void setHeight(double height) {
		this.height = height;
	}
	protected double getWidth() {
		return width;
	}
	protected void setWidth(double width) {
		this.width = width;
	}
	protected String getStyle() {
		return style;
	}
	protected void setStyle(String style) {
		this.style = style;
	}
	protected String getTechnique() {
		return technique;
	}
	protected void setTechnique(String technique) {
		this.technique = technique;
	}
	protected String getOrientation() {
		return orientation;
	}
	protected void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	protected double getPrice() {
		return price;
	}
	protected void setPrice(double price) {
		this.price = price;
	}
	protected double getRating() {
		return rating;
	}
	protected void setRating(double rating) {
		this.rating = rating;
	}
	protected boolean isRented() {
		return rented;
	}
	protected void setRented(boolean rented) {
		this.rented = rented;
	}
	
	public String toString(){
		return id + " " + name + " " + source + " " + artist + " " + height + " " + width + " " + style + " " + technique + " " + orientation + " " + rating + " " + price + " " +  rented;
	}
}