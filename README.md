# Fetch Rewards Coding Exercise - Software Engineering - Mobile

Native Android App in Kotlin that retrieves data from a remote API and displays it in a scrollable list using RecyclerView. 
The data is sorted based on the `listId` and `name` fields, and only entries with valid names are displayed.

## Quick Overview

- Gets JSON data from [Provided API](https://fetch-hiring.s3.amazonaws.com/hiring.json) using **OkHttp**
- Each JSON Object has the following structure: <pre> ```json { "id": int, "listId": int, "name": string | null } ``` </pre>
- Filters out data with empty or null name field and adds to an ArrayList
- Sorts the ArrayList by `listId`, then by `name`
- Displays the ArrayList in a scrollable list using **RecyclerView**