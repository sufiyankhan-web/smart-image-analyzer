function MetricProgress({ label, value, max = 100, suffix = '' }) {
  const pct = Math.max(0, Math.min(100, (value / max) * 100));

  return (
    <div className="space-y-2">
      <div className="flex items-center justify-between text-sm">
        <span className="text-brand-text">{label}</span>
        <span className="text-brand-muted">{value.toFixed(2)}{suffix}</span>
      </div>
      <div className="h-2 w-full rounded-full bg-white/10">
        <div
          className="h-2 rounded-full bg-gradient-to-r from-brand-accent to-brand-primary transition-all duration-500"
          style={{ width: `${pct}%` }}
        />
      </div>
    </div>
  );
}

export default MetricProgress;
