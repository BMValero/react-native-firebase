import firebase, { FirebaseMessagingTypes } from '.';

console.log(firebase.default().app);

// checks module exists at root
console.log(firebase.messaging().app.name);

// checks module exists at app level
console.log(firebase.app().messaging().app.name);

// checks statics exist
console.log(firebase.messaging.SDK_VERSION);
console.log(firebase.messaging.AuthorizationStatus.AUTHORIZED);
console.log(firebase.messaging.NotificationAndroidPriority.PRIORITY_LOW);
console.log(firebase.messaging.NotificationAndroidVisibility.VISIBILITY_PRIVATE);

// checks statics exist on defaultExport
console.log(firebase.SDK_VERSION);

// checks root exists
console.log(firebase.SDK_VERSION);

// checks firebase named export exists on module
console.log(firebase.firebase.SDK_VERSION);

// checks multi-app support exists
// console.log(firebase.messaging(firebase.app()).app.name);
//
// checks default export supports app arg
// console.log(defaultExport(firebase.app()).app.name);

firebase.messaging().subscribeToTopic('foo').then();
firebase.messaging().unsubscribeFromTopic('foo').then();
firebase.messaging().getAPNSToken().then();
firebase.messaging().setBackgroundMessageHandler((msg: FirebaseMessagingTypes.RemoteMessage) => {
  console.log(msg.data);
  return Promise.resolve();
});
