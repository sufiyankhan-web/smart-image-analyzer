import { Target, Sun, Layout, Minimize2, Waves } from 'lucide-react';

const MODULES = [
  {
    key: 'sharpnessScore',
    label: 'Sharpness',
    desc: 'Edge clarity & detail',
    Icon: Target,
    from: '#22d3ee',
    to: '#0891b2'
  },
  {
    key: 'exposureScore',
    label: 'Exposure',
    desc: 'Light & tone balance',
    Icon: Sun,
    from: '#fbbf24',
    to: '#d97706'
  },
  {
    key: 'compositionScore',
    label: 'Composition',
    desc: 'Framing & rule-of-thirds',
    Icon: Layout,
    from: '#818cf8',
    to: '#6366f1'
  },
  {
    key: 'symmetryScore',
    label: 'Symmetry',
    desc: 'Visual balance axis',
    Icon: Minimize2,
    from: '#34d399',
    to: '#059669'
  },
  {
    key: 'noiseScore',
    label: 'Noise',
    desc: 'Signal-to-noise ratio',
    Icon: Waves,
    from: '#f472b6',
    to: '#db2777'
  }
];

function ModuleScoreCards({ analysis }) {
  return (
    <div className="grid gap-3 sm:grid-cols-2 xl:grid-cols-5">
      {MODULES.map(({ key, label, desc, Icon, from, to }) => {
        const value = Math.round(analysis?.[key] || 0);
        const barWidth = Math.max(0, Math.min(100, value));

        return (
          <div
            key={key}
            className="glass-card animate-rise p-4 transition-all duration-200 hover:-translate-y-1 hover:border-white/10"
          >
            <div
              className="mb-3 inline-flex h-9 w-9 items-center justify-center rounded-lg ring-1 ring-white/8"
              style={{ backgroundColor: `${from}18`, color: from }}
            >
              <Icon className="h-4 w-4" />
            </div>

            <p className="text-xs font-semibold text-brand-text">{label}</p>
            <p className="text-[10px] text-brand-muted">{desc}</p>

            <p className="mt-3 font-black tracking-tight" style={{ color: from }}>
              <span className="text-2xl">{value}</span>
              <span className="ml-1 text-xs font-normal text-brand-muted">/100</span>
            </p>

            <div className="mt-2 h-1 w-full overflow-hidden rounded-full bg-white/8">
              <div
                className="h-full rounded-full transition-all duration-700"
                style={{
                  width: `${barWidth}%`,
                  backgroundImage: `linear-gradient(to right, ${from}, ${to})`
                }}
              />
            </div>
          </div>
        );
      })}
    </div>
  );
}

export default ModuleScoreCards;
