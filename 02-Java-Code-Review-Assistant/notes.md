# LLM Integration Learnings

## 1. Model quality matters

- Different LLMs follow instructions with different levels of reliability.
- Older/smaller models often ignored prompt instructions and returned responses wrapped in Markdown (` ```json ... ``` `).
- Newer and more capable models followed the prompt much more consistently and produced valid JSON.

---

## 2. Never trust the model output completely

Even with a well-written prompt, an LLM can still return:

- Markdown code fences
- Extra explanations
- Invalid JSON
- Missing fields
- Unexpected formatting

Always validate the response before using it.

---

## 3. Build a response parser/cleaner

Before deserializing the response:

- Remove Markdown code fences.
- Remove unwanted text before or after the JSON.
- Validate the JSON.
- Retry if parsing fails.

A small parser/cleaner makes the application much more reliable.

---

## 4. Implement retry with context

Instead of sending the same prompt again:

- Include the original request.
- Include the previous LLM response.
- Include the parser error.
- Ask the model to regenerate only valid JSON.

This significantly improves retry success rates.

---

## 5. Organize prompts

Instead of one huge prompt, split it into reusable sections:

- ROLE
- RESPONSE RULES
- JSON SCHEMA
- REVIEW GUIDELINES

Then compose the final prompt from these sections.

Benefits:

- Easier maintenance
- Better readability
- Easier to modify individual sections
- Reusable across multiple prompts

---

## 6. Prompt engineering is not enough

A good prompt improves output quality but is **not a guarantee**.

A production AI application should always combine:

- Strong prompt engineering
- Response validation
- Response cleaning
- Retry mechanism
- JSON parsing

Never rely on the prompt alone.

---

## 7. Production mindset

A robust LLM pipeline should look like this:

LLM
↓
Raw Response
↓
Response Cleaner
↓
JSON Validation
↓
ObjectMapper
↓
Success ✅

If parsing fails:

Retry Prompt (Original Prompt + Previous Response + Parser Error)
↓
LLM
↓
Repeat (up to a limited number of retries)

This approach is significantly more reliable than repeatedly sending the same prompt.