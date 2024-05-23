// src/api.js
import axios from "axios";

const apiKey = "sk-proj-HOYptj7bxt5cssYJJWGPT3BlbkFJEFgM9MDAEccr6T5ujRI9";
const apiEndpoint = "https://api.openai.com/v1/chat/completions";

export async function callOpenAI(message) {
  try {
    const response = await axios.post(
      apiEndpoint,
      {
        model: "gpt-3.5-turbo",
        messages: [
          { role: "system", content: "You are a helpful assistant." },
          { role: "user", content: message },
        ],
      },
      {
        headers: {
          Authorization: `Bearer ${apiKey}`,
          "Content-Type": "application/json",
        },
      }
    );
    return response.data.choices[0].message.content;
  } catch (error) {
    console.error("Error calling OpenAI API:", error);
    throw error;
  }
}
