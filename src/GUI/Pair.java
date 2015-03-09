package GUI;

public class Pair<E1,E2> {
	private E1 A;
	private E2 B;
	public Pair(E1 a, E2 b) {
		A = a;
		B = b;
	}
	public E1 first() {
		return A;
	}
	
	public E2 second() {
		return B;
	}
	
	@Override
	public int hashCode() {
		return A.hashCode() + B.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Pair<?,?>)) {
			return false;
		}
		Pair<E1,E2> oth = (Pair<E1,E2>) other;
		return A.equals(oth.first()) && B.equals(oth.second());
	}
}
