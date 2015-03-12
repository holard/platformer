package GUI;

public class Triple<E1,E2,E3> {
	private E1 A;
	private E2 B;
	private E3 C;
	
	public Triple(E1 a, E2 b, E3 c) {
		A = a;
		B = b;
		C = c;
	}
	public E1 first() {
		return A;
	}
	
	public E2 second() {
		return B;
	}
	
	public E3 third() {
		return C;
	}
	
	@Override
	public int hashCode() {
		return A.hashCode() + B.hashCode() + C.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Triple<?,?,?>)) {
			return false;
		}
		Triple<E1,E2,E3> oth = (Triple<E1,E2,E3>) other;
		return A.equals(oth.first()) && B.equals(oth.second()) && C.equals(oth.third());
	}
}
