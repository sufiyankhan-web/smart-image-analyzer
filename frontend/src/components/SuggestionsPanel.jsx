import { AlertTriangle, CheckCircle2, Lightbulb, Info } from 'lucide-react';

function classifySuggestion(text) {
  const lower = text.toLowerCase();
  if (lower.startsWith('great') || lower.includes('excellent') || lower.includes('well done') || lower.includes('looks good')) {
    return {
      Icon: CheckCircle2,
      color: '#34d399',
      bg: 'rgba(52,211,153,0.07)',
      border: 'rgba(52,211,153,0.18)'
    };
  }
  if (lower.includes('consider') || lower.includes('try') || lower.includes('tip') || lower.includes('scene')) {
    return {
      Icon: Info,
      color: '#22d3ee',
      bg: 'rgba(34,211,238,0.07)',
      border: 'rgba(34,211,238,0.18)'
    };
  }
  return {
    Icon: AlertTriangle,
    color: '#fbbf24',
    bg: 'rgba(251,191,36,0.07)',
    border: 'rgba(251,191,36,0.18)'
  };
}

function SuggestionsPanel({ suggestions = [] }) {
  return (
    <div className="glass-card flex flex-col p-6">
      <div className="mb-5 flex items-center gap-3">
        <span className="flex h-9 w-9 shrink-0 items-center justify-center rounded-xl bg-amber-400/10 text-amber-300">
          <Lightbulb className="h-5 w-5" />
        </span>
        <div>
          <h3 className="text-sm font-semibold text-white">Improvement Suggestions</h3>
          <p className="text-[11px] text-brand-muted">
            {suggestions.length} insight{suggestions.length !== 1 ? 's' : ''} found
          </p>
        </div>
      </div>

      {suggestions.length === 0 ? (
        <div className="flex flex-1 flex-col items-center justify-center gap-2 py-10 text-center">
          <CheckCircle2 className="h-8 w-8 text-emerald-400/40" />
          <p className="text-sm text-brand-muted">No issues detected. Image quality looks great!</p>
        </div>
      ) : (
        <ul className="space-y-2.5">
          {suggestions.map((item, idx) => {
            const { Icon, color, bg, border } = classifySuggestion(item);
            return (
              <li
                key={`${item}-${idx}`}
                className="flex gap-3 rounded-xl p-3 text-sm leading-relaxed"
                style={{ backgroundColor: bg, border: `1px solid ${border}` }}
              >
                <Icon className="mt-0.5 h-4 w-4 shrink-0" style={{ color }} />
                <span className="text-brand-text">{item}</span>
              </li>
            );
          })}
        </ul>
      )}
    </div>
  );
}

export default SuggestionsPanel;
