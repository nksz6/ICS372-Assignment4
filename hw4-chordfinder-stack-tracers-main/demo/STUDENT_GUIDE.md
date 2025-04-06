
# 👥 GitHub Classroom Student Guide – Eclipse Edition  
**ICS 372: Object-Oriented Design and Implementation**

## 📦 Getting Started with GitHub in Eclipse

### 🔗 Step 1: Accept the GitHub Classroom Link
- Click the invitation link from your instructor.
- Sign into GitHub and join your team.
- Your private team repository will be created.

Example repo:  
`https://github.com/MetropolitanStateCS/ics372-spring25-hw4-yourteam`

### 🧩 Step 2: Import the Repository into Eclipse
1. In Eclipse, go to **File > Import...**
2. Select:  
   `Git > Projects from Git` → Click **Next**
3. Choose **Clone URI** → Click **Next**
4. Paste the GitHub repo URL → Click **Next**
5. Choose the branch (`main`) → Click **Next**
6. Select where to save locally → Click **Next**
7. Choose **Import as General Project** → Finish

---

## 🌿 Working in a Team with Branches

### ➕ Create a Feature Branch (in Eclipse)
1. Right-click the project > **Team > Switch To > New Branch...**
2. Name it clearly: `feature-yourname-task` (e.g., `feature-sam-chord-parser`)
3. Check **Start working in the branch** → Finish

You are now working on your own feature branch.

---

## 💾 Making Changes and Committing

1. Save your changes in Eclipse.
2. Right-click the project > **Team > Commit...**
3. Write a helpful commit message.
4. Select the files you want to commit.
5. Click **Commit and Push** to send your changes to GitHub.

✅ Push regularly to avoid losing work and to sync with your team.

---

## 🔁 Pulling in Teammates' Changes

Before working each day:

1. Switch to `main`:  
   Right-click project > **Team > Switch To > main**
2. Right-click the project > **Team > Pull**

To bring updates into your feature branch:

1. Switch back to your branch  
   (Team > Switch To > your-feature-branch)
2. Right-click > **Team > Merge**  
   Select the `main` branch to merge changes into your branch

---

## 🚀 Submitting Code via Pull Requests

When your feature is ready:

1. Go to your repo on **GitHub.com**
2. Click the “Pull Requests” tab > **New Pull Request**
3. Choose your branch as the source, and `main` as the target
4. Add a description: what changed, what to test
5. Assign a teammate for review (`@teammate`)
6. Click **Create Pull Request**

Once it’s reviewed and approved, the team (or instructor) can **merge it**.

---

## ⚔️ Handling Merge Conflicts

Eclipse will alert you if a **merge conflict** happens.

Steps:

1. Eclipse will highlight files with conflicts.
2. Open the file and use the **Merge Tool** to compare versions:
   - “HEAD” = your version
   - “Incoming” = their version (from main or another branch)
3. Edit the file to keep the best parts of both.
4. After resolving:
   - Right-click project > **Team > Add to Index**
   - Then **Team > Commit** the resolved file

---

## 🔄 Commit & Push vs 🚀 Pull Request

| Action               | Purpose                          | Affects `main`? | When to Use                            |
|----------------------|----------------------------------|------------------|----------------------------------------|
| `Commit`             | Save changes locally             | ❌ No            | As you make progress on your task      |
| `Push`               | Upload your branch to GitHub     | ❌ No            | To sync work or share with teammates   |
| **Pull Request (PR)**| Request to merge into `main`     | ✅ Eventually    | When your feature is complete and reviewed |

> Think of `commit & push` as saving and syncing your draft. A **pull request** is how you submit it for review and merge into the team's final version.

---

## 👨‍👩‍👧 Team Branching Example

Imagine your team has 3 members with roles like this:

- **Student A**: Thymeleaf templates and HTML structure  
- **Student B**: Java service logic and model classes  
- **Student C**: JUnit testing and validation

Each student creates a branch:

- `feature-alex-thymeleaf`
- `feature-bella-service-model`
- `feature-cam-junit-tests`

They commit and push changes in their own branches, pull updates from `main`, and open **pull requests** when ready to merge.

This avoids conflicts and allows parallel development.

---

## 💬 Using GitHub Discussions

Each group repo has a **Discussions tab**:

Use it to:
- Divide tasks (e.g., backend, UI, testing)
- Ask/answer technical questions
- Coordinate merges or design decisions
- Share helpful resources

> ✅ It's visible to all teammates and the instructor

---

## ✅ Team Git Best Practices

| Practice | Why It Helps |
|----------|--------------|
| Use feature branches | Avoid overwriting each other’s work |
| Pull from main daily | Stay synced |
| Commit with messages | Track changes clearly |
| Use pull requests | Review and approve safely |
| Communicate in Discussions | Avoid confusion and duplication |

---

## 🆘 Troubleshooting Tips

- Use **Team > Fetch from Upstream** if push/pull seems stuck
- If your project gets messy, re-import it from Git
- Ask for help in Discussions or office hours
