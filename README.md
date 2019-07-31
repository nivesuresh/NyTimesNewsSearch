# NyTimesNewsSearch

For this app, I used the NY Times API to display news based on search keywords (used this API: https://developer.nytimes.com/docs/articlesearch-product/1/overview)

Things I chose to spend time on:

- Clean MVP architecture
- Parsing the endpoint, storing only the necessary models and displaying the image, headlines and a short snippet
- Paginating using offset and page
- Added search view to toolbar and used it to cleanly send queries to the endpoint
- Using the adapter to display the UI (for multimedia, I rendered the first element's url from the array)
- Creating a NetworkInterceptor to check for internet connection
- Created a caching mechanism for when there's no internet
- Showed snackbars for when there's no internet or cache

Things I could have done if I had more time:

- Having an empty-state screen
- Making the search view look/ feel better
- Placeholder image for when there's no multimedia URLs
