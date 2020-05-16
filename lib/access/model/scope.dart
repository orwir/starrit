enum Scope {
  identity,
  edit,
  flair,
  history,
  modconfig,
  modflair,
  modlog,
  modposts,
  modwiki,
  mysubreddits,
  privatemessages,
  read,
  report,
  save,
  submit,
  subscribe,
  vote,
  wikiedit,
  wikiread,
}

extension ScopeExtensions on Scope {
  String get label => toString().split('.')[1];
  String get parameter => label;
}
