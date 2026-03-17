/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif']
      },
      colors: {
        brand: {
          bg: '#070b14',
          panel: '#0d1424',
          panelSoft: '#121b30',
          primary: '#818cf8',
          primaryLight: '#c7d2fe',
          accent: '#22d3ee',
          accentLight: '#67e8f9',
          text: '#f1f5f9',
          muted: '#64748b',
          mutedLight: '#94a3b8'
        }
      },
      boxShadow: {
        card: '0 4px 32px rgba(0,0,0,0.5), 0 1px 4px rgba(0,0,0,0.4)',
        glow: '0 0 24px rgba(34,211,238,0.18)'
      },
      keyframes: {
        rise: {
          '0%': { opacity: '0', transform: 'translateY(12px)' },
          '100%': { opacity: '1', transform: 'translateY(0)' }
        },
        'fade-in': {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' }
        },
        pulse: {
          '0%, 100%': { opacity: '1' },
          '50%': { opacity: '0.5' }
        }
      },
      animation: {
        rise: 'rise 0.5s cubic-bezier(0.16,1,0.3,1)',
        'fade-in': 'fade-in 0.4s ease-out',
        pulse: 'pulse 2s cubic-bezier(0.4,0,0.6,1) infinite'
      }
    }
  },
  plugins: []
};
