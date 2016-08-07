# Dwitter (Twitter Client)

This is an Android application for reading and posting new tweets to Twitter.

Time spent: **6** hours spent in total

##User stories:
- [x] Required: User can sign in to Twitter using OAuth login
- [x] Required: User can view the tweets from their home timeline
- [x] Required: User should be displayed the username, name, and body for each
  tweet
- [x] Required: User should be displayed the relative timestamp for each tweet
  "8m", "7h"
- [x] Required: User can view more tweets as they scroll with infinite
  pagination
- [x] Required: User can compose a new tweet
- [x] Required: User can click a “Compose” icon in the Action Bar on the top
  right
- [x] Required: User can then enter a new tweet and post this to twitter
- [x] Required: User is taken back to home timeline with new tweet visible in
  timeline

- [x] Optional: While composing a tweet, user can see a character counter with
  characters remaining for tweet out of 140
- [x] Optional: Links in tweets are clickable and will launch the web browser
  (see autolink)
- [x] Optional: User can refresh tweets timeline by pulling down to refresh (i.e
  pull-to-refresh)
- [ ] Optional: User can open the twitter app offline and see last loaded tweets
- [ ] Optional: User can tap a tweet to display a "detailed" view of that tweet
- [ ] Optional: User can select "reply" from detail view to respond to a tweet
- [ ] Optional: Improve the user interface and theme
- [ ] Optional: User can see embedded image media within the tweet detail view
- [ ] Optional: User can watch embedded video within the tweet
- [ ] Optional: Compose activity is replaced with a modal overlay
- [ ] Optional: Use Parcelable instead of Serializable using the popular
  Parceler library.
- [x] Optional: Apply the popular Butterknife annotation library to reduce view
  boilerplate.
- [x] Optional: Leverage RecyclerView as a replacement for the ListView and
  ArrayAdapter for all lists of tweets.
- [x] Optional: Move the "Compose" action to a FloatingActionButton instead of
  on the AppBar.
- [x] Optional: Replace all icon drawables and other static image assets with
  vector drawables where appropriate.
- [ ] Optional: Leverage the data binding support module to bind data into one
  or more activity or fragment layout templates.
- [x] Optional: Replace Picasso with Glide for more efficient image rendering.

##Walkthrough of all user stories:
<img src='https://github.com/realdlee/Dwitter/blob/master/walkthrough.gif'
title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## License

Copyright 2016 David Lee

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
