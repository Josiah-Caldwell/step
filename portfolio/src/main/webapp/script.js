// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!','Hallo Walt', 
      'It is an undeniable and may I say FUNDAMENTAL quality of man that, when faced ith extinction, EVERY alternative is preferable.'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
/** 
 * Adds a comment to the top of the page
 */
function greetJSONString() {
  fetch('/data').then(response => response.text()).then((json) => {
    document.getElementById('named-greeting-container').innerText = json;
  });
  console.log("Add JSON String: "/*Get text from fetch*/);
}

function fetchComments() {
  const commentsPromise = fetch("/data?" + "number-comments=" + document.getElementById("number-comments").value); 
  commentsPromise.then(response => response.text()).then(loadComments);
}

/**
 * Loads existing comments under comment box
 * @param ArrayList<String> comments
 */
function loadComments(comments) {
  const commentList = document.getElementById("stored-comments-container");
  console.log(commentList);
  //Remove all children to prevent dusplicates
  while (commentList.firstChild) {
    commentList.removeChild(commentList.firstChild);
  }

  for (comment of comments) {
    const commentListItem = document.createElement('li');
    commentListItem.innerText = comment;
    commentList.appendChild(commentListItem);
    console.log(comment);
    //console.log(commentList);
  }
  console.log("Add employee comments: "/*Get text from fetch*/);
}

function commentString() {
  fetch("/data?" + "number-comments=" + document.getElementById("number-comments").value).then(response => response.text()).then((json) => {
    document.getElementById('stored-comments-container').innerText = json;
  });
  console.log("Add comment String to list: "/*Get text from fetch*/);
}
function deleteComments() {
  const commentList = document.getElementById("stored-comments-container");
  //Remove all children to prevent dusplicates
  while (commentList.firstChild) {
    commentList.removeChild(commentList.firstChild);
  }
}