function getGrade(score) {
  if (score >= 85) return { letter: 'A', label: 'Excellent', color: '#34d399', glow: 'rgba(52,211,153,0.35)' };
  if (score >= 70) return { letter: 'B', label: 'Great', color: '#22d3ee', glow: 'rgba(34,211,238,0.35)' };
  if (score >= 55) return { letter: 'C', label: 'Good', color: '#818cf8', glow: 'rgba(129,140,248,0.35)' };
  if (score >= 40) return { letter: 'D', label: 'Fair', color: '#fbbf24', glow: 'rgba(251,191,36,0.35)' };
  return { letter: 'F', label: 'Needs Work', color: '#f87171', glow: 'rgba(248,113,113,0.35)' };
}

function ScoreRing({ score = 0 }) {
  const radius = 64;
  const stroke = 9;
  const normalized = Math.max(0, Math.min(100, score));
  const circumference = 2 * Math.PI * radius;
  const dashOffset = circumference - (normalized / 100) * circumference;
  const grade = getGrade(normalized);

  return (
    <div className="glass-card flex flex-col items-center justify-center p-6">
      <p className="text-[11px] font-semibold uppercase tracking-widest text-brand-muted">Quality Score</p>

      <div className="relative mt-5 h-44 w-44">
        <svg className="h-44 w-44 -rotate-90" viewBox="0 0 160 160" aria-label="quality-score-ring">
          {/* Track */}
          <circle
            cx="80" cy="80" r={radius}
            stroke="rgba(255,255,255,0.05)"
            strokeWidth={stroke}
            fill="none"
          />
          {/* Progress */}
          <circle
            cx="80" cy="80" r={radius}
            stroke={grade.color}
            strokeWidth={stroke}
            fill="none"
            strokeLinecap="round"
            strokeDasharray={circumference}
            strokeDashoffset={dashOffset}
            style={{
              filter: `drop-shadow(0 0 10px ${grade.glow})`,
              transition: 'stroke-dashoffset 0.9s cubic-bezier(0.16,1,0.3,1)'
            }}
          />
        </svg>
        <div className="absolute inset-0 flex flex-col items-center justify-center">
          <p className="text-4xl font-black tracking-tight text-white">{Math.round(normalized)}</p>
          <p className="text-[11px] text-brand-muted">/100</p>
        </div>
      </div>

      <div className="mt-4 flex items-center gap-2.5">
        <span
          className="rounded-lg px-2.5 py-0.5 text-sm font-black"
          style={{ backgroundColor: `${grade.color}18`, color: grade.color }}
        >
          {grade.letter}
        </span>
        <span className="text-sm font-medium text-brand-text">{grade.label}</span>
      </div>
    </div>
  );
}

export default ScoreRing;
