 Simple app to showcase Popular photos from 500px.

### Design

 ![Component diagram](./out/design/uml/component/Component%20Diagram.png)

 The app uses the MVVM architectural pattern with a thin view that is only responsible for displaying UI elements and responding to user action. The pagination is handled using the `PagedList` and `DataSource+Factory` elements from the android paging library in combination with a `PagedListAdpater`. The `Pagedlist` internally manages the pagination and coordinates with the Data source to load new pages of data to be displayed. The app can be improved by adding RoomDB as a local cache for the photo items fetched from the backend but as of now the solution is a network only soulution and will not work offline.

 ![Models](./out/design/uml/model/Models.png)