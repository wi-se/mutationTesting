package mutation.testing.sample;

public class Sample {
	int x;
	int y;

	public boolean isXEqual(int value) {
		return value == x;
	}

	public boolean isYEqual(int value) {
		return value == y;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void add(int value) {
		x += value;
	}

	public void sub(int value) {
		x -= value;
	}

	public void mult(int value) {
		x *= value;
	}

	public void div(int value) {
		x /= value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}
