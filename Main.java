class Main {
    public static void main(String[] args) {
        //test data
        String[][] ipData = {
          {"1.0.0.0", "1.0.0.255", "AU"},
          {"1.1.1.0", "1.1.1.255", "CN"},
          {"2.0.0.0", "2.255.255.255", "FR"},
          {"5.5.5.0", "5.5.5.50", "DE"},
          {"8.8.8.0", "8.8.8.255", "US"},
          {"23.0.0.0", "23.255.255.255", "US"},
          {"31.0.0.0", "31.255.255.255", "GB"},
          {"45.0.0.0", "45.255.255.255", "CA"},
          {"62.0.0.0", "62.255.255.255", "IT"},
          {"84.0.0.0", "84.255.255.255", "ES"},
          {"103.0.0.0", "103.255.255.255", "JP"},
          {"151.101.0.0", "151.101.255.255", "US"},
          {"172.217.0.0", "172.217.255.255", "IE"},
          {"185.0.0.0", "185.255.255.255", "NL"},
          {"210.0.0.0", "210.255.255.255", "KR"}
        };
        
        AddressTree tree = new AddressTree(ipData);

        //testing
        System.out.println("8.8.8.8: " + tree.find("8.8.8.8"));       // Should be US
        System.out.println("2.1.1.1: " + tree.find("2.1.1.1"));       // Should be FR
        System.out.println("4.4.4.4: " + tree.find("4.4.4.4"));       // Should be null (Gap)
        System.out.println("210.1.1.1: " + tree.find("210.1.1.1"));   // Should be KR
        System.out.println("DONE");
    }
}

