package edu.lambdaandstream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class GroupByDemo {
    public static void main(String[] args) {
        List<PermissionGroup> permissionGroups = PermissionGroup.createPermissionGroups();
        System.out.println(permissionGroups);

        System.out.println();

        Map<String, List<PermissionGroup>> groupByExternalId = permissionGroups.stream()
            .collect(Collectors.groupingBy(PermissionGroup::getExternalId));
        for (Map.Entry<String, List<PermissionGroup>> entry : groupByExternalId.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        System.out.println();

        Map<String, PermissionGroup> map = permissionGroups
            .stream()
            .collect(Collectors.toMap(
                PermissionGroup::getExternalId,
                Function.identity(),
                (o, n) -> new PermissionGroup(o.getName() + "--" + n.getName(), o.getExternalId()))
            );
        map.entrySet().forEach(System.out::println);
    }
}


class PermissionGroup {
    static List<PermissionGroup> createPermissionGroups() {
        List<PermissionGroup> permissionGroups = new ArrayList<>();
        permissionGroups.add(new PermissionGroup("name1.1", "id1"));
        permissionGroups.add(new PermissionGroup("name2.1", "id2"));
        permissionGroups.add(new PermissionGroup("name2.2", "id2"));
        permissionGroups.add(new PermissionGroup("name3.1", "id3"));
        permissionGroups.add(new PermissionGroup("name3.2", "id3"));
        permissionGroups.add(new PermissionGroup("name3.3", "id3"));
        permissionGroups.add(new PermissionGroup("name4.1", "id4"));
        permissionGroups.add(new PermissionGroup("name4.2", "id4"));
        permissionGroups.add(new PermissionGroup("name4.3", "id4"));
        permissionGroups.add(new PermissionGroup("name4.4", "id4"));
        return permissionGroups;
    }

    private String name;
    private String externalId;

    PermissionGroup(String name, String externalId) {
        this.name = name;
        this.externalId = externalId;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getExternalId() {
        return externalId;
    }

    void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public String toString() {
        return "PermissionGroup{" +
            "name='" + name + '\'' +
            ", externalId='" + externalId + '\'' +
            '}';
    }
}
