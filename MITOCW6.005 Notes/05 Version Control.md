# Distributed vs. centralized

### Centralized

Traditional centralized version control systems like CVS and Subversion. They support a collaboration graph – who’s sharing what changes with whom – with one master server and copies that only communicate with the master.

In a centralized system, everyone must share their work to and from the master repository. Changes are safely stored in version control if they are in the master repository, because that’s the only repository.

### Distributed

In contrast, distributed version control systems like Git and Mercurial allow all sorts of different collaboration graphs, where teams and subsets of teams can experiment easily with alternate versions of code and history, merging versions together as they are determined to be good ideas.

In a distributed system, all repositories are created equal, and it’s up to users to assign them different roles. Different users might share their work to and from different repos, and the team must decide what it means for a change to be in version control.

## Version control terminology

- **Repository** : a local or remote store of the versions in our project
- **Working copy** : a local, editable copy of our project that we can work on
- **File** : a single file in our project
- **Version** or **revision** : a record of the contents of our project at a point in time
- **Change** or **diff** : the difference between two versions
- **Head** : the current version

## Features of a version control system

- **Reliable** : keep versions around for as long as we need them; allow backups
- **Multiple files** : track versions of a project, not single files
- **Meaningful versions** : what were the changes, why where they made?
- **Revert** : restore old versions, in whole or in part
- **Compare versions**
- **Review history** : for the whole project or individual files
- **Not just for code** : prose, images, …

It should **allow multiple people to work together** :

- **Merge** : combine versions that diverged from a common previous version
- **Track responsibility** : who made that change, who touched that line of code?
- **Work in parallel** : allow one programmer to work on their own for a while (without giving up version control)
- **Work-in-progress** : allow multiple programmers to share unfinished work (without disrupting others, without giving up version control)

# Git

# The Git object graph

All of the operations we do with Git — clone, add, commit, push, log, merge, … — are operations on a graph data structure that stores all of the versions of files in our project, and all the log entries describing those changes. The Git object graph is stored in the .git directory of your local repository.

The history of a Git project is a directed acyclic graph (DAG). The history graph is the backbone of the full object graph stored in .git , so let’s focus on it for a minute.

Each node in the history graph is a commit a.k.a. version a.k.a. revision of the project: a complete snapshot of all the files in the project at that point in time. Each commit is identified by a unique ID.

Except for the initial commit, each commit has a pointer to its parent commit. For example, commit 1255f4e has parent 41c4b8f : this means 41c4b8f happened first, then 1255f4e.

Some commits have the same parent: they are versions that diverged from a common previous version. And some commits have two parents: they are versions that tie divergent histories back together.

Finally, HEAD points to our current commit — almost. We also need to remember which branch we’re working on. So HEAD points to the current branch, which points to the current commit.

**The Git object graph stores each version of an individual file once , and allows multiple commits to share that one copy. To the left is a more complete rendering of the Git object graph for our example.**

![hello-git-history-trees.png](../_resources/hello-git-history-trees.png)

# Importance of Version Control

- **Safe from bugs.** find when and where something broke look for other, similar mistakes gain confidence that code hasn’t changed accidentally.
- **Easy to understand.** why was a change made?, what else was changed at the same time?, who can I ask about this code? .
- **Ready for change.** all about managing and organizing changes, accept and integrate changes from other developers isolate speculative work on branches.

# More on Git

- https://git-scm.com/book/en/v2
- https://git-scm.com/docs