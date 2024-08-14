# Git Convention (Template)

```
# "[<type>]: <title> <body> <footer>"

# Title has to be < 50 chars: ex) [feat]: Add Key Mapping

# Body has to be written here

# Footer then is written here: ex) Github issue #23

# ex) [feat]: Add signin, signup  
  
Added signin and sign up features

Resolves: #1

# --- COMMIT END ---
#   <type> Lists
#   feat        : features (new features)
#   fix         : bugs (bug fixes)
#   refactor    : refactoring
#   comment     : change in references
#   style       : style (code formats, semicolons: no changes in business logic)
#   docs        : changes in documents (add, patch delete, README)
#   test        : test (new test codes, changes, deletes: no changes in logic)
#   chore       : etc (build scripts, assets, 패키지 매니저 등)
#   init        : initialisation
#   rename      : rename files or directories: no changes in code 
#   remove      : delete files or directories: no changes in code
#   change      : changed a file content
#   doc         : a new document file 
# ------------------
#   First Character of a titile to always be a Capital Letter 
#   no '.' at the end of the title
#   separate liner after the title
#   in the body put "What" and "Why" than "How"
#   to separate lines in the body put '-'
# ------------------
#   <footer>
#   optional not mandatory
#   Fixes        : still fixing the issues
#   Resolves     : resolves the issues
#   Ref          : reference to other issues
#   Related to   : reference to related commits (when not resolved)
#   ex) Fixes: #47 Related to: #32, #21
```