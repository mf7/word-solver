import java.util.Comparator;

public class LengthComparator implements Comparator<String> {
	@Override
	public int compare(String s, String ss){
		int one_lenth = s.length();
		int two_lenth = ss.length();
		if(one_lenth < two_lenth) return -1;
		else if(one_lenth > two_lenth) return 1;
		else return s.compareTo(ss); /*Default to alphabetical*/
	}
}
