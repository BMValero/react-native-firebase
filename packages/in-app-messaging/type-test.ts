import firebase from '.';

console.log(firebase.default().app);

// checks module exists at root
console.log(firebase.inAppMessaging().app.name);
console.log(firebase.inAppMessaging().isMessagesDisplaySuppressed);

// checks module exists at app level
console.log(firebase.app().inAppMessaging().app.name);
console.log(firebase.app().inAppMessaging().isMessagesDisplaySuppressed);

// checks statics exist
console.log(firebase.inAppMessaging.SDK_VERSION);

// checks statics exist on defaultExport
console.log(firebase.SDK_VERSION);

// check module correctly exported
console.log(firebase.default().isMessagesDisplaySuppressed);

// checks root exists
console.log(firebase.SDK_VERSION);

// checks firebase named export exists on module
console.log(firebase.firebase.SDK_VERSION);

firebase
  .inAppMessaging()
  .setAutomaticDataCollectionEnabled(false)
  .then();
