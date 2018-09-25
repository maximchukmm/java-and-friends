package edu.other;

public class SplitSqlInsert {
    public static void main(String[] args) {
        String[] inserts = sql.split("\n");

        for (int i = 0; i < inserts.length; i++) {
            String[] insert = inserts[i].split("',");
            for (int j = 0; j < insert.length; j++) {
                if (j == 2) {
                    System.out.print(":groupId, ");
                } else if (j != insert.length-1) {
                    System.out.print(insert[j] + "', ");}
                    else {
                    System.out.println(insert[j]);
                }
            }
        }
    }

    private static final String sql = "INSERT INTO schedule_scheme (id, name, group_id, external_id, max_day, date) VALUES ('a1c0e41a-4cb8-4cda-bab4-969f30e3cacf', 'Без графика', '300425a8-2983-acd4-cba8-57e69f0a9d5a', 'a1c0e41a-4cb8-4cda-bab4-969f30e3cacf', 28, '2017-12-04');\n";
}
