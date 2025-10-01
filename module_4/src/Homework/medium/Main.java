package Homework.medium;

public class Main {
    public static void main(String[] args) {
        DataSource<MyData> myDataDataSource = new Repository<>(
                new CachedDataSource<>(), new MyDataCloudDataSource());

        DataSource<GeoData> geoDataDataSource = new GeoRepository(
                new CachedDataSource<>(), new GeoDataCloudDataSource());

        DataSource<UserData> userDataSource = new UserRepository(
                new CachedDataSource<>(), new UserDataCloudDataSource());

        MyData myData = myDataDataSource.getData();
        GeoData geoData = geoDataDataSource.getData();
        UserData userDataFirst = userDataSource.getData();  // из "сети"
        UserData userDataCached = userDataSource.getData(); // из кэша

        System.out.println(myData.toString());
        System.out.println(geoData.toString());
        System.out.println(userDataFirst.toString());
        System.out.println(userDataCached.toString());
    }

}