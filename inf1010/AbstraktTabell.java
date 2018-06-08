import java.lang.Iterable;
interface AbstraktTabell<T> extends Iterable<T>{
	public void dobblePlassITabell();
	public boolean settInnPaaOppgittPlass(int index, T element);
	public T finnObjektEtterNokkel(String keystring);
	public T finnObjektPaaOppgittPlass(int index);
	//public Iterator<T> iterator();
}