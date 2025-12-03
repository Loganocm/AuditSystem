/// <reference types="vite/client" />

// Augment Vite's ImportMeta typing so import.meta.env is recognized
interface ImportMetaEnv {
  readonly VITE_API_URL?: string;
  // add other VITE_ variables here as needed
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
