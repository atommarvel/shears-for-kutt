# Shears for Kutt

✂️ An unofficial Android client for [Kutt](http://kutt.it/).

# Maintenance Approach

As long as Kutt is hosted by someone and I have a free account that I can use custom domains on I am
happy to maintain the app as much as my personal time permits!

That being said... Donations help me prioritize my side projects :)

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/S6S64QETK)

# TODO Tracker

## Alpha Phase

- [x] Created example of fetching a user's urls to validate that the APIs are usable and the project
  is worth doing
- [x] Figma [wireframe prototype](data/kutt-android-wireframe.gif) created
- [x] Add LICENSE
- [x] Jetpack Navigation Setup
- [x] API key persistence
- [x] Setup ktlint
- [x] Settings Screen
- [x] Create app
  icon. [Kutt logo usage approved](https://github.com/thedevs-network/kutt/issues/165#issuecomment-848490148)
- [x] Rename app to Shears
- [ ] convert pngs to webp
- [ ] Finalize color scheme (based on Kutt.it?)
- [ ] Use Kutt font?

### Home
- [ ] Home Screen
- [ ] Direct users to settings screen when no api key provided
- [ ] Snackbar errors
- [ ] Home Screen Paging
- [ ] Create Link Screen
- [ ] Link Options Modal
- [ ] Delete links via link options modal

### Table Stakes
- [ ] Share urls to app to create shortened urls
- [ ] Text selection url shortening
- [ ] UI Polish
- [ ] Android 12 Splash screen

### Production Ready
- [ ] Setup Crash Analytics
- [ ] Setup Play Store force upgrade lib
- [ ] Setup In-app review lib
- [ ] Create translation system for strings
- [ ] Ask for translators in settings screen
- [ ] Decide and implement maintenance model
- [ ] Unit tests and documentation
- [ ] Do a pass on all marked TODOs
- [ ] Double check accessibility
- [ ] Setup CI/CD
- [ ] [App Launch Checklist](https://developer.android.com/distribute/best-practices/launch/launch-checklist)
- [ ] Update README with contributing process, setup + installation.
- [ ] Create static landing website for app (hosted by github?)
- [ ] Release Open Beta
- [ ] Add [get on play store badge](https://play.google.com/intl/en_gb/badges/)

## Open Beta Phase
- [ ] API key-less UX
- [ ] API key-less Implementation
- [ ] Update Link Screen
- [ ] Manage domains Screen
- [ ] Manage domains Info Modal
- [ ] Search Created Links UX
- [ ] Search Created Links Implementation
- [ ] Exit Beta. Fully release app.

## Stable Phase

### Exit Strategy
- [ ] Export all links to a csv for easy migration
- [ ] Automate importing a csv and creating short links for the data
- [ ] Add the ability to work with self-hosted kutt instances

### Stats
- [ ] Stats Screen UX
- [ ] Stats Screen Implementation

### QR
- [ ] View QR Code UX
- [ ] View QR Code Implementation

### Material You
- [ ] Integrate with "Material You" once Compose updates to support it. 