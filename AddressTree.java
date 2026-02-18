import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class AddressTree {
    private final Node root;

    //init
    public AddressTree(String[][] rawData) {
        // ingest range data
        List<IpRange> ranges = new ArrayList<>();
        for (String[] record : rawData) {
            ranges.add(new IpRange(record));
        }
        ranges.sort(Comparator.comparingLong(rec -> rec.start));
        // grow the tree!
        this.root = growTree(ranges, 0, ranges.size() - 1);
    }

    //make the tree here
    private Node growTree(List<IpRange> sortedRanges, int left, int right) {
        if (left > right) return null;

        int mid = left + (right - left) / 2;
        IpRange range = sortedRanges.get(mid);
        //fun (would normally avoid recursion but takes too long to write now)
        Node node = new Node(range.start, range.end, range.country);
        node.left = growTree(sortedRanges, left, mid - 1);
        node.right = growTree(sortedRanges, mid + 1, right);
        return node;
    }
    
    //Takes ip as string, returns country code or null if not found
    public String find(String ipString) {
        long target = ipToLong(ipString);
        Node current = root;
        //crawl
        while (current != null) {
            if (target >= current.start && target <= current.end) {
                return current.country;
            }
            current = (target < current.start) ? current.left : current.right;
        }
        return null;
    }

    //data stucts
    private static class Node {
        long start, end;
        String country;
        Node left, right;

        Node(long s, long e, String c) {
            this.start = s; this.end = e; this.country = c;
        }
    }

    private static class IpRange {
        long start, end;
        String country;

        IpRange(String[] record) {
            // Reusing the static method from the outer class
            this.start = ipToLong(record[0]);
            this.end = ipToLong(record[1]);
            this.country = record[2];
        }
    }

    //shared utils
    private static long ipToLong(String ip) {
        String[] octets = ip.split("\\.");
        long result = 0;
        for (String octet : octets) {
            result <<= 8;
            result |= (Integer.parseInt(octet) & 0xFF);
        }
        return result;
    }
}
